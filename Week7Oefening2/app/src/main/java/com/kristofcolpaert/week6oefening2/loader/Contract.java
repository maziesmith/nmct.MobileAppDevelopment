package com.kristofcolpaert.week6oefening2.loader;

import android.provider.BaseColumns;

/**
 * Created by kristofcolpaert on 31/03/15.
 */
public final class Contract
{
    public interface StudentColumns extends BaseColumns
    {
        public static final String COLUMN_STUDENT_NAME = "student_name";
        public static final String COLUMN_STUDENT_FIRSTNAME = "student_firstname";
        public static final String COLUMN_STUDENT_EMAIL = "student_email";
        public static final String COLUMN_STUDENT_SCORE_TOTAL = "student_score_total";
    }

    public interface ModulePuntColumns extends BaseColumns
    {
        public static final String COLUMN_MODULE_NAAM = "module_naam";
        public static final String COLUMN_MODULE_SCORE = "module_score";
        public static final String COLUMN_MODULE_STUDIEPUNTEN = "module_studiepunten";
    }
}
