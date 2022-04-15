package dev.firebase.appdistribution.nautmlauncher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import dev.firebase.appdistribution.nautmlauncher.databinding.FragmentFirstBinding
import java.lang.IllegalArgumentException


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

  private var _binding: FragmentFirstBinding? = null
  // This property is only valid between onCreateView and onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    _binding = FragmentFirstBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    Log.e(TAG, "Setting on click listener")
    binding.launchNautmButton.setOnClickListener { launchNautm() }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun launchNautm() {
    Log.e(TAG, "Launching NAUTM")
    val launchIntent: Intent? = requireContext().packageManager.getLaunchIntentForPackage(packageName())

    if (launchIntent == null) {
      Toast.makeText(requireContext(), "Could not launch package '${packageName()}'", Toast.LENGTH_SHORT).show()
      Log.e(TAG, "Could not launch package '${packageName()}'")
      return
    }

    if (binding.buildVersion.text.isNotEmpty()) {
      launchIntent.putExtra("currentVersionOverride", binding.buildVersion.text.toString().toInt())
    }
    if (binding.rolloutBucket.text.isNotEmpty()) {
      launchIntent.putExtra("rolloutBucketOverride", binding.rolloutBucket.text.toString().toInt())
    }
    Log.e(TAG, "Launching intent: ${launchIntent}")
    startActivity(launchIntent)
  }

  private fun packageName(): String {
    return when (binding.variant.text.toString()) {
      "release" -> "dev.firebase.appdistribution"
      "debug", "internal", "beta" -> "dev.firebase.appdistribution.${binding.variant.text}"
      else -> throw IllegalArgumentException("Invalid variant: '${binding.variant.text}'. Must be one of 'debug', 'internal', 'beta', or 'release'.")
    }
  }

  companion object {
    const val TAG = "FirstFragment"
  }
}