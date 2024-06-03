package com.example.gougou.ui.notifications
import android.widget.Button
import android.net.Uri
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gougou.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btn:Button=binding.button
        btn.setOnClickListener {
            val phoneNumber = "2381992348" // Reemplaza con el número de WhatsApp deseado
            openWhatsApp(phoneNumber)
        }

        val textView: TextView = binding.textNo
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun openWhatsApp(phoneNumber: String) {
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$2381992348")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}