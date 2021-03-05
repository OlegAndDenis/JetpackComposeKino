package com.example.kino.db.migration

import androidx.room.migration.Migration
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class MigrationModule {

    @IntoSet
    @Provides
    fun provideMigration_1_2(): Migration {
        return Migration_1_2()
    }

    @IntoSet
    @Provides
    fun provideMigration_2_3(): Migration {
        return Migration_2_3()
    }
}