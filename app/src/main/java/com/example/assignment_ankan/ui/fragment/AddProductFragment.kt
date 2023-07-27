package com.example.assignment_ankan.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.assignment_ankan.R
import com.example.assignment_ankan.model.AddProductResponse
import com.example.assignment_ankan.utill.checkNetworkState
import com.example.assignment_ankan.viewModel.AddProductViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.*

class AddProductFragment : Fragment() {
    var imageUri:Uri? = null
    lateinit var progressBar:ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_add_product, container, false)
        val textViewProductName = view.findViewById<EditText>(R.id.edit_text_product_name)
        val textViewProductType = view.findViewById<EditText>(R.id.edit_text_product_type)
        val textViewPrice = view.findViewById<EditText>(R.id.edit_text_price)
        val textViewTax = view.findViewById<EditText>(R.id.edit_text_tax)
        val textViewUpload = view.findViewById<TextView>(R.id.text_view_upload)
        val imageView = view.findViewById<ImageView>(R.id.image_view)
        val buttonAdd = view.findViewById<Button>(R.id.button_add)
        progressBar = view.findViewById<ProgressBar>(R.id.progress_bar_add_product)

        val contract = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                imageView.setImageURI(uri)
            }
        }
        textViewUpload.setOnClickListener {
            contract.launch("image/*")
        }

        buttonAdd.setOnClickListener{
            val productName: String = textViewProductName.text.toString()
            val productType: String = textViewProductType.text.toString()
            val price: String = textViewPrice.text.toString()
            val tax: String = textViewTax.text.toString()

            if(productName!="" || productType!="" || price!="" || tax!="") {
                var imagePart: MultipartBody.Part? = null
                if(!isValidDecimalNumber(price)){
                    Toast.makeText(activity,"Enter price correctly", Toast.LENGTH_SHORT).show()
                }else if(!isValidDecimalNumber(tax)){
                    Toast.makeText(activity,"Enter tax correctly", Toast.LENGTH_SHORT).show()
                }else if(imageUri!=null){
                    val fileDirectory = requireContext().filesDir
                    val file = File(fileDirectory,"image.png")
                    val inputStream = requireContext().contentResolver.openInputStream(imageUri!!)
                    val outputStream = FileOutputStream(file)
                    inputStream!!.copyTo(outputStream)
                    val reqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                    imagePart = MultipartBody.Part.createFormData("files[]",file.name,reqBody)
                }
                progressBar.visibility = View.VISIBLE
                val productRequestBody = productName.toRequestBody("text/plain".toMediaTypeOrNull())
                val productTypeRequestBody = productType.toRequestBody("text/plain".toMediaTypeOrNull())
                val priceRequestBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
                val taxRequestBody = tax.toRequestBody("text/plain".toMediaTypeOrNull())
                Log.d("Upload_Image","imageUri: $imageUri\nimagePart: $imagePart")

                initViewModel(productRequestBody, productTypeRequestBody, priceRequestBody, taxRequestBody, imagePart)
            }
            else
                Toast.makeText(activity,"Enter Data Correctly", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    private fun isValidDecimalNumber(s: String?): Boolean {
        return !s.isNullOrEmpty() && s.matches(Regex("\\d+(\\.\\d+)?"))
    }

    companion object {
        fun newInstance() =
            AddProductFragment()
    }

    private fun initViewModel(
        productName: RequestBody,
        productType: RequestBody,
        price: RequestBody,
        tax: RequestBody,
        imagePart: MultipartBody.Part?
    ){
        if(checkNetworkState(requireContext())){
            val viewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)
            viewModel.addProductObserver().observe(this,androidx.lifecycle.Observer<AddProductResponse>{
                progressBar.visibility = View.GONE
                Toast.makeText(activity,"Product Added Successfully", Toast.LENGTH_SHORT).show()
            })
            viewModel.makeApiCall(productName,productType,price,tax,imagePart)
        }else{
            Toast.makeText(activity,"Check Internet Connectivity", Toast.LENGTH_SHORT).show()
        }
    }
}