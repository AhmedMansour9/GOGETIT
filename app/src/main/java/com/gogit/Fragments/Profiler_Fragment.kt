package com.gogit.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.gogit.Activties.Navigation
import com.gogit.Activties.ui.gallery.GalleryViewModel
import com.gogit.Model.Edit_ProfileResponse
import com.gogit.Model.Profile_Response
import com.gogit.Model.Register_Model
import com.gogit.R
import com.gogit.ViewModel.EditProfile_ViiewModel
import com.gogit.ViewModel.Profile_ViewModel
import com.gogit.ViewModel.Register_ViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_all_products_.view.*
import kotlinx.android.synthetic.main.fragment_editprofile.view.*
import kotlinx.android.synthetic.main.fragment_editprofile.view.toolbarProducts

class Profiler_Fragment : Fragment() {

    private lateinit var DataSaver: SharedPreferences
    private lateinit var galleryViewModel: GalleryViewModel
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    lateinit var UserToken: String
    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_editprofile, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!
        init()
        Get_Profle()
        Rela_ChangeData()
        Rela_Password()
        Btn_ConfirmEmail()
        Btn_ConFirmpassword()
        return root
    }

    fun Rela_ChangeData() {
        root.Constrain_ChangeData.setOnClickListener() {
            root.Constrain_ChangeData.setBackgroundColor(Color.WHITE);
            root.T_Signin.setTextColor(
                ContextCompat.getColor(
                    context!!.applicationContext,
                    R.color.gray
                )
            )
            root.Constrain_ChangePass.setBackgroundColor(
                ContextCompat.getColor(
                    context!!.applicationContext,
                    R.color.gray
                )
            )
            root.T_Signup.setTextColor(Color.WHITE)
            root.constrain_Password.visibility = View.GONE
            root.constrain_Login.visibility = View.VISIBLE
        }


    }

    fun Rela_Password() {
        root.Constrain_ChangePass.setOnClickListener() {
            root.Constrain_ChangeData.setBackgroundColor(
                ContextCompat.getColor(
                    context!!.applicationContext,
                    R.color.gray
                )
            );
            root.T_Signin.setTextColor(Color.WHITE)
            root.Constrain_ChangePass.setBackgroundColor(Color.WHITE)
            root.T_Signup.setTextColor(
                ContextCompat.getColor(
                    context!!.applicationContext,
                    R.color.gray
                )
            )
            root.constrain_Password.visibility = View.VISIBLE
            root.constrain_Login.visibility = View.GONE

        }


    }
    fun init() {

        val toggle = ActionBarDrawerToggle(
            activity,
            Navigation.drawerLayout,
            root.toolbarProducts,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.apply {
            syncState()
            isDrawerIndicatorEnabled = false
            setHomeAsUpIndicator(R.drawable.icon_menu)
            setToolbarNavigationClickListener { Navigation.drawerLayout!!.openDrawer(GravityCompat.START) }
        }
        Navigation.drawerLayout?.addDrawerListener(toggle)

    }
    fun Get_Profle() {
        var Prof_ViewModel: Profile_ViewModel =
            ViewModelProviders.of(this)[Profile_ViewModel::class.java]
        root.progressBarLogin.visibility = View.VISIBLE

        Prof_ViewModel.getData(
            UserToken,

            context!!.applicationContext
        ).observe(this,
            Observer<Profile_Response> { loginmodel ->

                progressBarLogin.visibility = View.GONE
                if (loginmodel != null) {
                    root.E_NameProfile.setText(loginmodel.data.get(0).name)
                    root.E_Phone.setText(loginmodel.data.get(0).phone)
                    root.E_Email.setText(loginmodel.data.get(0).email)
                } else {
                    Toast.makeText(
                        context!!.applicationContext,
                        context!!.applicationContext.getString(R.string.failedmsg),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }

    fun Btn_ConfirmEmail() {
        root.Btn_ConfirmEmail.setOnClickListener() {

            var Name = root.E_NameProfile.text.toString()
            var Phone = root.E_Phone.text.toString()
            var email = root.E_Email.text.toString()
            if (Name.isEmpty()) {
                root.E_NameProfile.error = "Name required"
                root.E_NameProfile.requestFocus()
            }
            if (Phone.isEmpty()) {
                root.E_Phone.error = "Phone required"
                root.E_Phone.requestFocus()
            }
            if (email.isEmpty()) {
                root.E_Email.error = "Email required"
                root.E_Email.requestFocus()
            }
            if (!isEmailValid(email)) {
                root.E_Email.error = "Valid Email required"
                root.E_Email.requestFocus()
            } else if (!Name.isEmpty() && !Phone.isEmpty() && !email.isEmpty()) {

                if (isConnected) {
                    var editProf_ViewModel: EditProfile_ViiewModel =
                        ViewModelProviders.of(this)[EditProfile_ViiewModel::class.java]
                    progressBarLogin.visibility = View.VISIBLE

                    editProf_ViewModel.getData(
                        UserToken,
                        email,
                        Phone,
                        Name,
                        context!!.applicationContext
                    ).observe(this,
                        Observer<Edit_ProfileResponse> { loginmodel ->

                            progressBarLogin.visibility = View.GONE
                            if (loginmodel != null) {
                                Toast.makeText(
                                    context!!.applicationContext,
                                    context!!.applicationContext.getString(R.string.updated),
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    context!!.applicationContext,
                                    context!!.applicationContext.getString(R.string.failedmsg),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    )
                } else {
                    Toast.makeText(
                        context!!.applicationContext,
                        context!!.applicationContext.getString(R.string.nointernet),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    fun isEmailValid(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email);
    }

    fun Btn_ConFirmpassword() {
        root.Btn_ConfirmPass.setOnClickListener(){
            var Password = root.e_Password.text.toString().trim()
            var ConfirmPassword = root.e_PasswordConFirm.text.toString().trim()
            if (Password.isEmpty()) {
                root.e_Password.error = "Password required"
                root.e_Password.requestFocus()
            }
            if (ConfirmPassword.isEmpty()) {
                root.e_PasswordConFirm.error = "ConFirm Password required"
                root.e_PasswordConFirm.requestFocus()
            }
            if (Password.length < 6 || Password.length > 16) {
                root.e_Password.error =
                    "Min password must be at least 6 Chrachter and  max 16 Chrachter "
                root.e_Password.requestFocus()
            }
            if (ConfirmPassword.length < 6 || ConfirmPassword.length > 16) {
                root.e_PasswordConFirm.error =
                    "Min password must be at least 6 Chrachter and  max 16 Chrachter "
                root.e_PasswordConFirm.requestFocus()
            }


            else if (!Password.isEmpty() && Password.length >= 6 || Password.length <= 16
                && ConfirmPassword.length >= 6 || ConfirmPassword.length <= 16
            ) {
                if (isConnected) {
                    var editProf_ViewModel: EditProfile_ViiewModel =
                        ViewModelProviders.of(this)[EditProfile_ViiewModel::class.java]
                    progressBarLogin.visibility = View.VISIBLE

                    editProf_ViewModel.getConfirmPassowrd(
                        UserToken,
                        Password,
                        ConfirmPassword,
                        context!!.applicationContext
                    ).observe(this,
                        Observer<Edit_ProfileResponse> { loginmodel ->

                            progressBarLogin.visibility = View.GONE
                            if (loginmodel != null) {
                                if(loginmodel.data.equals("Password does not match ")){
                                    Toast.makeText(
                                        context!!.applicationContext,
                                        getString(R.string.oldpasswrong),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }else if (loginmodel.data.equals("Password changed successfully")) {
                                    Toast.makeText(
                                        context!!.applicationContext,
                                        getString(R.string.updatedpassword),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    context!!.applicationContext,
                                    getString(R.string.failedmsg),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    )
                } else {
                    Toast.makeText(
                        context!!.applicationContext,
                        context!!.applicationContext.getString(R.string.nointernet),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }

    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    val isConnected: Boolean
        get() {
            return (context!!.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }



}