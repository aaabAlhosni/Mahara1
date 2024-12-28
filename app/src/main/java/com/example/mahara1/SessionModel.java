package com.example.mahara1;

public class SessionModel {
    String sessions;
    int sessionsID;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    int resId;
    public SessionModel(String sessions, int sessionsID, int resID) {
        this.sessions = sessions;
        this.sessionsID = sessionsID;
        this.resId = resID;
    }

    public String getSessions() {
        return sessions;
    }

    public void setSessions(String sessions) {
        this.sessions = sessions;
    }

    public int getSessionsID() {
        return sessionsID;
    }

    public void setSessionsID(int sessionsID) {
        this.sessionsID = sessionsID;
    }
}
