package com.crammr.studyflashcard.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.crammr.studyflashcard.data.local.AppDatabase;
import com.crammr.studyflashcard.data.local.dao.QuizResultDao;
import com.crammr.studyflashcard.data.local.dao.StudySessionDao;
import com.crammr.studyflashcard.data.local.entity.QuizResult;
import com.crammr.studyflashcard.data.local.entity.StudyHistory;
import com.crammr.studyflashcard.data.local.entity.StudySession;

import java.util.List;

public class StudyRepository {
    private final StudySessionDao studySessionDao;
    private final QuizResultDao quizResultDao;

    public StudyRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        studySessionDao = db.studySessionDao();
        quizResultDao = db.quizResultDao();
    }

    public LiveData<List<StudySession>> getAllSessionsLive() {
        return studySessionDao.getAllSessionsLive();
    }

    public LiveData<Integer> getTotalStudyMinutesLive() {
        return studySessionDao.getTotalStudyMinutesLive();
    }

    public LiveData<Integer> getTotalCardsLearnedLive() {
        return studySessionDao.getTotalCardsLearnedLive();
    }

    public void insertSession(StudySession session, List<StudyHistory> historyList) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            long sessionId = studySessionDao.insertSession(session);
            if (historyList != null) {
                for (StudyHistory history : historyList) {
                    history.setSessionId(sessionId);
                    studySessionDao.insertHistory(history);
                }
            }
        });
    }

    public LiveData<List<QuizResult>> getAllQuizResultsLive() {
        return quizResultDao.getAllResultsLive();
    }

    public LiveData<Float> getAverageScoreLive() {
        return quizResultDao.getAverageScoreLive();
    }

    public LiveData<Integer> getTotalQuizzesLive() {
        return quizResultDao.getTotalQuizzesLive();
    }

    public void insertQuizResult(QuizResult result) {
        AppDatabase.databaseWriteExecutor.execute(() -> quizResultDao.insert(result));
    }
}
