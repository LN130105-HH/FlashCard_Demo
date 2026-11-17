package com.crammr.studyflashcard.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.crammr.studyflashcard.data.local.dao.CollectionDao;
import com.crammr.studyflashcard.data.local.dao.ExerciseDao;
import com.crammr.studyflashcard.data.local.dao.FlashcardDao;
import com.crammr.studyflashcard.data.local.dao.NotificationDao;
import com.crammr.studyflashcard.data.local.dao.QuizResultDao;
import com.crammr.studyflashcard.data.local.dao.ReminderDao;
import com.crammr.studyflashcard.data.local.dao.StudySessionDao;
import com.crammr.studyflashcard.data.local.dao.SubjectDao;
import com.crammr.studyflashcard.data.local.dao.TopicDao;
import com.crammr.studyflashcard.data.local.dao.UserDao;
import com.crammr.studyflashcard.data.local.entity.AnswerOption;
import com.crammr.studyflashcard.data.local.entity.Exercise;
import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;
import com.crammr.studyflashcard.data.local.entity.NotificationItem;
import com.crammr.studyflashcard.data.local.entity.Question;
import com.crammr.studyflashcard.data.local.entity.QuizResult;
import com.crammr.studyflashcard.data.local.entity.Reminder;
import com.crammr.studyflashcard.data.local.entity.StudyHistory;
import com.crammr.studyflashcard.data.local.entity.StudySession;
import com.crammr.studyflashcard.data.local.entity.Subject;
import com.crammr.studyflashcard.data.local.entity.Topic;
import com.crammr.studyflashcard.data.local.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        User.class,
        Subject.class,
        Topic.class,
        FlashcardCollection.class,
        Flashcard.class,
        Exercise.class,
        Question.class,
        AnswerOption.class,
        StudySession.class,
        StudyHistory.class,
        QuizResult.class,
        Reminder.class,
        NotificationItem.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDao userDao();
    public abstract SubjectDao subjectDao();
    public abstract TopicDao topicDao();
    public abstract CollectionDao collectionDao();
    public abstract FlashcardDao flashcardDao();
    public abstract ExerciseDao exerciseDao();
    public abstract StudySessionDao studySessionDao();
    public abstract QuizResultDao quizResultDao();
    public abstract ReminderDao reminderDao();
    public abstract NotificationDao notificationDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "crammr_flashcard_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                if (INSTANCE == null) return;

                // Prepopulate Starter Subject & Topic
                Subject csSubject = new Subject("Computer Science", "Fundamental CS principles and algorithms", "ic_library", "#9CE5F4");
                long csId = INSTANCE.subjectDao().insert(csSubject);

                Topic dsTopic = new Topic(csId, "Data Structures", "Arrays, Linked Lists, Trees, Stacks, Queues");
                long dsTopicId = INSTANCE.topicDao().insert(dsTopic);

                // Prepopulate Starter Collection
                FlashcardCollection collection = new FlashcardCollection(csId, dsTopicId, "Essential Data Structures", "Master core data structures and time complexities for technical interviews.");
                collection.setCardCount(5);
                collection.setProgress(40);
                long colId = INSTANCE.collectionDao().insert(collection);

                // Prepopulate Flashcards
                INSTANCE.flashcardDao().insert(new Flashcard(colId,
                        "What is an Array and its primary access time complexity?",
                        "An Array is a contiguous memory structure storing items of the same type. Direct index access runs in O(1) time.",
                        "Direct pointer arithmetic enables instant element indexing."));

                INSTANCE.flashcardDao().insert(new Flashcard(colId,
                        "What is a Linked List and its primary benefit?",
                        "A linear sequence of nodes with data and next pointers. It allows O(1) insertions/deletions at known positions without contiguous memory reallocation.",
                        "Unlike arrays, linked lists do not require contiguous memory blocks."));

                INSTANCE.flashcardDao().insert(new Flashcard(colId,
                        "Explain the LIFO principle of a Stack.",
                        "Last-In, First-Out: The most recently added element is the first one removed via pop().",
                        "Used for function call stacks, undo mechanisms, and parsing."));

                INSTANCE.flashcardDao().insert(new Flashcard(colId,
                        "What is a Binary Search Tree (BST) invariant?",
                        "For every node X, all keys in its left subtree are less than X, and all keys in its right subtree are greater than X.",
                        "Enables O(log n) average search, insertion, and deletion."));

                INSTANCE.flashcardDao().insert(new Flashcard(colId,
                        "What is the difference between BFS and DFS?",
                        "BFS explores level-by-level using a FIFO Queue; DFS explores as deep as possible along each branch using recursion or a LIFO Stack.",
                        "BFS is ideal for shortest path in unweighted graphs."));

                // Prepopulate Welcome Notification
                INSTANCE.notificationDao().insert(new NotificationItem(
                        "Welcome to Crammr!",
                        "Start your learning journey by exploring the Essential Data Structures deck or uploading your own notes.",
                        "SYSTEM"
                ));
            });
        }
    };
}
