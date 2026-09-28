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

@Database(
    entities = [
        UserAccountEntity::class,
        UserProfile::class,
        SubscriptionPlan::class,
        CuratedMatchEntity::class,
        ChatMessageEntity::class,
        InvoiceEntity::class,
        SafetyReportEntity::class,
        PlatformConfigEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class MaitryDatabase : RoomDatabase() {

    abstract fun maitryDao(): MaitryDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun subscriptionPlanDao(): SubscriptionPlanDao

    companion object {
        @Volatile
        private var INSTANCE: MaitryDatabase? = null

        fun getDatabase(context: Context): MaitryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MaitryDatabase::class.java,
                    "maitry_app.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
