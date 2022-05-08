package com.xendv.ReportLoader.conf

class DBConfig {
    companion object {
        val HOST = "localhost"
        val PORT = 5432
        val DB_NAME = "ftei_db"
        val USERNAME = "postgres"
        val PASSWORD = ""
        val connectionQuery = "host=" + HOST + " port=" + PORT + " dbname=" + DB_NAME +
                " user=" + USERNAME + " password=" + PASSWORD +
                " options='--client_encoding=UTF8'";
    }
}