package com.example.arogyasahayalocal.ui

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object Patients : Screen("patients")
    object PatientDetail : Screen("patient_detail/{patientId}") {
        fun createRoute(patientId: Int) = "patient_detail/$patientId"
    }
    object Records : Screen("records/{patientId}") {
        fun createRoute(patientId: Int) = "records/$patientId"
    }
    object Medicines : Screen("medicines/{patientId}") {
        fun createRoute(patientId: Int) = "medicines/$patientId"
    }
    object Vitals : Screen("vitals/{patientId}") {
        fun createRoute(patientId: Int) = "vitals/$patientId"
    }
    object Doctors : Screen("doctors")
    object Staff : Screen("staff")
    object Settings : Screen("settings")
    object Profile : Screen("profile")
    object MedicalReports : Screen("medical_reports")
    object Meetups : Screen("meetups")
}
