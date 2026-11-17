package com.crammr.studyflashcard.data.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.crammr.studyflashcard.data.local.AppDatabase;
import com.crammr.studyflashcard.data.local.dao.UserDao;
import com.crammr.studyflashcard.data.local.entity.User;
import com.crammr.studyflashcard.utils.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepository {
    private final UserDao userDao;
    private final PreferenceManager preferenceManager;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        preferenceManager = new PreferenceManager(application);

        try {
            mAuth = FirebaseAuth.getInstance();
            mFirestore = FirebaseFirestore.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            mAuth = null;
            mFirestore = null;
        }
    }

    public LiveData<User> getCurrentUserLive() {
        return userDao.getCurrentUserLive();
    }

    public boolean isLoggedIn() {
        if (mAuth != null && mAuth.getCurrentUser() != null) {
            return true;
        }
        return preferenceManager.isLoggedIn();
    }

    public void login(String email, String password, boolean rememberMe, AuthCallback callback) {
        if (mAuth != null) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            FirebaseUser fbUser = task.getResult().getUser();
                            String uid = fbUser != null ? fbUser.getUid() : UUID.randomUUID().toString();
                            String displayName = fbUser != null && fbUser.getDisplayName() != null
                                    ? fbUser.getDisplayName() : extractNameFromEmail(email);

                            // Fetch extra profile data from Firestore
                            if (mFirestore != null) {
                                mFirestore.collection("users").document(uid).get()
                                        .addOnCompleteListener(docTask -> {
                                            String finalName = displayName;
                                            String bio = "Preparing for study sprints";
                                            String gender = "Not specified";

                                            if (docTask.isSuccessful() && docTask.getResult() != null && docTask.getResult().exists()) {
                                                DocumentSnapshot doc = docTask.getResult();
                                                String dbName = doc.getString("fullName");
                                                if (dbName != null && !dbName.isEmpty()) finalName = dbName;
                                                String dbBio = doc.getString("bio");
                                                if (dbBio != null) bio = dbBio;
                                                String dbGender = doc.getString("gender");
                                                if (dbGender != null) gender = dbGender;
                                            }

                                            saveSessionAndLocalUser(uid, email, finalName, password, bio, gender, rememberMe, callback);
                                        });
                            } else {
                                saveSessionAndLocalUser(uid, email, displayName, password, "Preparing for study sprints", "Not specified", rememberMe, callback);
                            }
                        } else {
                            // Check offline local database fallback
                            String errorMsg = task.getException() != null ? task.getException().getMessage() : "Authentication failed";
                            attemptOfflineLogin(email, password, rememberMe, errorMsg, callback);
                        }
                    });
        } else {
            attemptOfflineLogin(email, password, rememberMe, null, callback);
        }
    }

    private void attemptOfflineLogin(String email, String password, boolean rememberMe, String originalError, AuthCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User localUser = userDao.getUserByEmail(email);
            if (localUser != null) {
                // Check password match if local password stored
                if (localUser.getPassword() == null || localUser.getPassword().equals(password)) {
                    preferenceManager.setLoggedIn(true);
                    preferenceManager.setRememberMe(rememberMe);
                    preferenceManager.setUserEmail(email);
                    preferenceManager.setUserName(localUser.getFullName());
                    if (callback != null) {
                        new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(localUser));
                    }
                    return;
                }
            }

            if (callback != null) {
                String msg = originalError != null ? originalError : "Tài khoản hoặc mật khẩu không chính xác";
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(msg));
            }
        });
    }

    public void register(String fullName, String email, String password, AuthCallback callback) {
        if (mAuth != null) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            FirebaseUser fbUser = task.getResult().getUser();
                            String uid = fbUser != null ? fbUser.getUid() : UUID.randomUUID().toString();

                            // Update Display Name in Firebase Auth Profile
                            if (fbUser != null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(fullName)
                                        .build();
                                fbUser.updateProfile(profileUpdates);
                            }

                            // Save user profile info in Firestore (including password for recovery)
                            if (mFirestore != null) {
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("uid", uid);
                                userData.put("fullName", fullName);
                                userData.put("email", email);
                                userData.put("password", password);
                                userData.put("createdAt", System.currentTimeMillis());
                                userData.put("bio", "Preparing for study sprints");
                                userData.put("gender", "Not specified");

                                mFirestore.collection("users").document(uid).set(userData);
                            }

                            saveSessionAndLocalUser(uid, email, fullName, password, "Preparing for study sprints", "Not specified", true, callback);
                        } else {
                            String errorMsg = task.getException() != null ? task.getException().getMessage() : "Registration failed";
                            if (callback != null) {
                                callback.onError(errorMsg);
                            }
                        }
                    });
        } else {
            // Local fallback registration
            String uid = UUID.randomUUID().toString();
            saveSessionAndLocalUser(uid, email, fullName, password, "Preparing for study sprints", "Not specified", true, callback);
        }
    }

    private void saveSessionAndLocalUser(String uid, String email, String fullName, String password,
                                         String bio, String gender, boolean rememberMe, AuthCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = userDao.getUserByEmail(email);
            if (user == null) {
                user = new User(uid, email, fullName, password);
                user.setBio(bio);
                user.setGender(gender);
                long id = userDao.insert(user);
                user.setId(id);
            } else {
                user.setUid(uid);
                user.setFullName(fullName);
                user.setPassword(password);
                user.setBio(bio);
                user.setGender(gender);
                userDao.update(user);
            }

            preferenceManager.setLoggedIn(true);
            preferenceManager.setRememberMe(rememberMe);
            preferenceManager.setUserEmail(email);
            preferenceManager.setUserName(fullName);

            final User finalUser = user;
            if (callback != null) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(finalUser));
            }
        });
    }

    public void retrievePasswordByEmail(String email, PasswordCallback callback) {
        if (mFirestore != null) {
            mFirestore.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                            DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                            String pwd = doc.getString("password");
                            if (pwd != null && !pwd.isEmpty()) {
                                if (callback != null) callback.onSuccess(pwd);
                                return;
                            }
                        }

                        // Check local Room database if not found in Firestore query
                        checkLocalPassword(email, callback);
                    });
        } else {
            checkLocalPassword(email, callback);
        }
    }

    private void checkLocalPassword(String email, PasswordCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User localUser = userDao.getUserByEmail(email);
            new Handler(Looper.getMainLooper()).post(() -> {
                if (localUser != null && localUser.getPassword() != null && !localUser.getPassword().isEmpty()) {
                    if (callback != null) callback.onSuccess(localUser.getPassword());
                } else {
                    if (callback != null) callback.onError("Không tìm thấy tài khoản đã đăng ký với địa chỉ Gmail: " + email);
                }
            });
        });
    }

    public void logout() {
        if (mAuth != null) {
            try {
                mAuth.signOut();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        preferenceManager.clearSession();
        AppDatabase.databaseWriteExecutor.execute(userDao::clearUsers);
    }

    public void updateUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.update(user);
            preferenceManager.setUserName(user.getFullName());

            if (mFirestore != null && user.getUid() != null) {
                Map<String, Object> updates = new HashMap<>();
                updates.put("fullName", user.getFullName());
                updates.put("bio", user.getBio());
                updates.put("gender", user.getGender());
                mFirestore.collection("users").document(user.getUid()).update(updates);
            }
        });
    }

    public String getUserName() {
        return preferenceManager.getUserName();
    }

    public String getUserEmail() {
        return preferenceManager.getUserEmail();
    }

    private String extractNameFromEmail(String email) {
        if (email != null && email.contains("@")) {
            String name = email.substring(0, email.indexOf("@"));
            return Character.toUpperCase(name.charAt(0)) + (name.length() > 1 ? name.substring(1) : "");
        }
        return "Learner";
    }

    public interface AuthCallback {
        void onSuccess(User user);
        void onError(String message);
    }

    public interface PasswordCallback {
        void onSuccess(String password);
        void onError(String message);
    }
}
