package com.example.daftarmahasiswa.ui

import android.content.ContentProviderClient
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.daftarmahasiswa.R
import com.example.daftarmahasiswa.application.StudentApp
import com.example.daftarmahasiswa.databinding.FragmentSecondBinding
import com.example.daftarmahasiswa.model.Student
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory((applicationContext as StudentApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var student: Student? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLang: LatLng? = null
    private lateinit var  fusedLocationClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        student = args.student
        // kita cek jika student null maka tampilan default nambah daftar mahasiswa
        // jika student tidak null tampilan sedikit berubah ada tombol hapus dan ubah
        if(student != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(student?.name)
            binding.majorEditText.setText(student?.major)
            binding.clazzEditText.setText(student?.clazz)
            binding.addressEditText.setText(student?.address)
        }

        // binding google map
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

        val name = binding.nameEditText.text
        val major = binding.majorEditText.text
        val clazz = binding.clazzEditText.text
        val address = binding.addressEditText.text

        binding.saveButton.setOnClickListener {
            // kita kasih kondisi jika kosong tidak bisa nyimpan
            if(name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if(major.isEmpty()) {
            Toast.makeText(context,"jurusan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if(clazz.isEmpty()) {
            Toast.makeText(context,"Kelas tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if(address.isEmpty()) {
            Toast.makeText(context,"Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
        else {
                // jika udah berhasil run
                // kita masukkan data latitude longitude sebenarnya
                if(student == null){
                    val student = Student(student?.id!!, name.toString(), major.toString(), clazz.toString(), address.toString(), currentLatLang?.latitude, currentLatLang?.longitude)
                    studentViewModel.insert(student)
                } else {
                    val student = Student(0, name.toString(), major.toString(), clazz.toString(), address.toString(), currentLatLang?.latitude, currentLatLang?.longitude)
                    studentViewModel.update(student)
                }

                findNavController().popBackStack() //untuk dismiss halaman ini
            }
        }

        binding.deleteButton.setOnClickListener {
            student?.let { studentViewModel.delete(it) }
            findNavController().popBackStack()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // implement drag marker
        mMap.setOnMarkerDragListener(this)

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {
    }

    private fun checkPermission() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
            applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            Toast.makeText(applicationContext, "Akses lokasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        // ngecek jika permission tidak disetujuimaka akan berhenti di kondisi if
        if (ContextCompat.checkSelfPermission(
            applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
        // untuk test current location coba run langsung atau build apknya terus install di hp masing-masing
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    var latLang = LatLng(location.latitude, location.longitude)
                    currentLatLang = latLang
                    var title = "Marker"

                    // menampilkan lokasi sesuai koordinat yang sudah disimpan atau diupdate tadi
                    if (student != null) {
                        title = student?.name.toString()
                        val newCurrentLocation = LatLng(student?.latitude!!, student?.longitude!!)
                        latLang = newCurrentLocation
                    }
                    val markerOption = MarkerOptions()
                        .position(latLang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.house))
                    mMap.addMarker(markerOption)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang, 15f))
                }
            }
    }
}