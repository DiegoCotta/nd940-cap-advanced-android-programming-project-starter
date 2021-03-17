package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.utils.gone
import com.example.android.politicalpreparedness.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class VoterInfoFragment : Fragment() {

    private lateinit var binding: FragmentVoterInfoBinding
    val viewModel: VoterInfoViewModel by viewModel(
        clazz = VoterInfoViewModel::class,
        qualifier = null,
        parameters = {
            parametersOf(
                VoterInfoFragmentArgs.fromBundle(arguments!!).argElection
            )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        setObservables()

        return binding.root
    }

    private fun setObservables() {
        viewModel.voterInfo.observe(viewLifecycleOwner) { voterInfoResponse ->
            voterInfoResponse?.state?.get(0)?.electionAdministrationBody?.let { administrationBody ->
                if (administrationBody.ballotInfoUrl != null || administrationBody.votingLocationFinderUrl != null) {
                    binding.stateHeader.visible()
                    if (administrationBody.ballotInfoUrl != null)
                        binding.stateBallot.visible()
                    else
                        binding.stateBallot.gone()
                    if (administrationBody.votingLocationFinderUrl != null)
                        binding.stateLocations.visible()
                    else
                        binding.stateLocations.gone()
                }
                if (administrationBody.correspondenceAddress != null) {
                    binding.addressGroup.visible()
                    binding.address.text =
                        administrationBody.correspondenceAddress.toFormattedString()
                } else
                    binding.addressGroup.gone()
            }
        }

        viewModel.urlBrowser.observe(viewLifecycleOwner) { url ->
            if (url != null) {
                openBrowser(url)
            }
        }
    }

    private fun openBrowser(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
        viewModel.openBrowserCompleted()
    }

}