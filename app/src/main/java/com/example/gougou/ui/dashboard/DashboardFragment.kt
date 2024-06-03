package com.example.gougou.ui.dashboard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gougou.*
import com.example.gougou.Ruta3
import com.example.gougou.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {


    private var _binding: FragmentDashboardBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        binding.btn1.setOnClickListener {
            val fragmen = Ruta1()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fragmen)?.commit()

        }
        binding.btn2.setOnClickListener {
            val fr = Ruta2()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr)?.commit()

        }
        binding.btn3.setOnClickListener {
            val f = Ruta3()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, f)?.commit()

        }
        binding.btn4.setOnClickListener {
            val f1 = Ruta4()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, f1)?.commit()

        }
        binding.btn5.setOnClickListener {
            val fr3 = Ruta5()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr3)?.commit()

        }
        binding.btn6.setOnClickListener {
            val fr6 = Ruta6()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr6)?.commit()

        }
        binding.btn7.setOnClickListener {
            val fr7 = Ruta7()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr7)?.commit()

        }
        binding.btn8.setOnClickListener {
            val fr371 = RutaUPN()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr371)?.commit()

        }
        binding.btn9.setOnClickListener {
            val fr9 = Granjas()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr9)?.commit()

        }
        binding.btn10.setOnClickListener {
            val fr10 = Ruta8()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr10)?.commit()

        }
        binding.btn11.setOnClickListener {
            val fr11 = Ruta9()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr11)?.commit()

        }
        binding.btn12.setOnClickListener {
            val fr12 = Ruta10()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr12)?.commit()

        }
        binding.btn13.setOnClickListener {
            val fr14 = Ruta11()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr14)?.commit()

        }
        binding.btn14.setOnClickListener {
            val fr122 = Ruta12()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr122)?.commit()

        }
        binding.btn15.setOnClickListener {
            val fr13 = Ruta13()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr13)?.commit()

        }
        binding.btn16.setOnClickListener {
            val fr15 = Ruta15()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr15)?.commit()

        }
        binding.btn17.setOnClickListener {
            val fr17 = Ruta16()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr17)?.commit()

        }
        binding.btn19.setOnClickListener {
            val fr28 = Ruta17()//ruta28
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr28)?.commit()

        }
        binding.btn18.setOnClickListener {
            val fr24 = Fragment24()//ruta24
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr24)?.commit()
        }
        binding.btn20.setOnClickListener {
            val fr31 = fragment31()//ruta31
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr31)?.commit()
        }
        binding.btn21.setOnClickListener {
            val fr42 = Ruta42()//ruta21
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr42)?.commit()
        }
        binding.btn23.setOnClickListener {
            val fr43 = Ruta43()//ruta30
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_menu, fr43)?.commit()
        }










            val root: View = binding.root
            val textView: TextView = binding.btn1
            //textDashboard
            dashboardViewModel.text.observe(viewLifecycleOwner) {
                textView.text = it
            }
            return root
        }


        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

}
