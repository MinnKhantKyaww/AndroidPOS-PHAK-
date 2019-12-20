package com.team.androidpos.ui;

public interface Dismissible {

    interface OnDismissedListener {
        void onDismissied();
    }

    void dismiss(OnDismissedListener OnDismissedListener);
}
