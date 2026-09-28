package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.MaitryDao
import com.example.data.local.dao.SubscriptionPlanDao
import com.example.data.local.dao.UserProfileDao
import com.example.data.local.entity.ChatMessageEntity
import com.example.data.local.entity.CuratedMatchEntity
import com.example.data.local.entity.InvoiceEntity
import com.example.data.local.entity.PlatformConfigEntity
import com.example.data.local.entity.SafetyReportEntity
import com.example.data.local.entity.SubscriptionPlan
import com.example.data.local.entity.UserAccountEntity
import com.example.data.local.entity.UserProfile

/**
 * AppDatabase abstract class extending RoomDatabase to integrate the 'UserProfile' entity,
 * 'SubscriptionPlan' entity, and corresponding DAOs into the application's local storage infrastructure.
 */
@Database(
    entities = [
        UserProfile::class,
        SubscriptionPlan::class,
        UserAccountEntity::class,
        CuratedMatchEntity::class,
        ChatMessageEntity::class,
        InvoiceEntity::class,
        SafetyReportEntity::class,
        PlatformConfigEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Data Access Object for handling CRUD operations on the UserProfile entity.
     */
    abstract fun userProfileDao(): UserProfileDao

    /**
     * Data Access Object for tracking annual and 6-month subscription statuses.
     */
    abstract fun subscriptionPlanDao(): SubscriptionPlanDao

    /**
     * Data Access Object for general application and platform operations.
     */
    abstract fun maitryDao(): MaitryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Returns the singleton instance of [AppDatabase].
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
