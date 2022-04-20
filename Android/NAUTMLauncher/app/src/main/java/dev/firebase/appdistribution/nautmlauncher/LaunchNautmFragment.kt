package dev.firebase.appdistribution.nautmlauncher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import dev.firebase.appdistribution.nautmlauncher.databinding.FragmentLaunchNautmBinding
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException


/** The main fragment for the application. */
class LaunchNautmFragment : Fragment() {

  private var _binding: FragmentLaunchNautmBinding? = null
  // This property is only valid between onCreateView and onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    _binding = FragmentLaunchNautmBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.launchNautmButton.setOnClickListener { launchNautm() }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun launchNautm() {
    Log.i(TAG, "Launching NAUTM")
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
    Log.i(TAG, "Launching intent: ${launchIntent}")
    startActivity(launchIntent)
  }

  private fun packageName(): String {
    return if (binding.radioRelease.isChecked) { "dev.firebase.appdistribution"}
    else if (binding.radioBeta.isChecked) { "dev.firebase.appdistribution.beta"; }
    else if (binding.radioInternal.isChecked) { "dev.firebase.appdistribution.internal"; }
    else if (binding.radioDebug.isChecked) { "dev.firebase.appdistribution.debug"; }
    else { throw IllegalStateException("There should always be exactly one variant selected"); }
  }

  companion object {
    const val TAG = "LaunchNautmFragment"
  }
}