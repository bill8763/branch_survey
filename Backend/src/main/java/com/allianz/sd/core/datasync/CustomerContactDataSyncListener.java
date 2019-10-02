package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Note;
import com.allianz.sd.core.jpa.model.CustomerContact;

public interface CustomerContactDataSyncListener {
    public void postSave(CustomerContact customerContact, Note note);

    public void onPullData(CustomerContact customerContact, Note note);
}
