package com.suspend.android.learntodrive.ui.simulation

import androidx.navigation.fragment.findNavController
import com.suspend.android.learntodrive.base.BaseFragment
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.databinding.FragmentSimulationBinding
import com.suspend.android.learntodrive.model.ItemSimulation
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "SimulationFragment"

class SimulationFragment :
    BaseFragment<FragmentSimulationBinding>(FragmentSimulationBinding::inflate) {

    override val viewModel by viewModel<SimulationViewModel>()
    private val listAdapterHomeItem by lazy { ListAdapterSimulationItem(::onClickItem) }

    override fun initData() {

    }

    override fun initView() {
        binding.recyclerViewHome.adapter = listAdapterHomeItem
        listAdapterHomeItem.submitList(mutableListOf(*ItemSimulation.values()))

    }

    override fun initEvent() {

    }


    private fun onClickItem(item: ItemSimulation) {

        when (item) {
            ItemSimulation.THEORETICAL -> {
                // SimulationFragmentDirections.actionNavigationSimulationToNavigationVideoPlayer()
                findNavController().navigateSafe(R.id.action_navigation_simulation_to_navigation_learn_simulation_fragment)
            }

           /* ItemSimulation.TIPS_ANSWER -> {
                //SimulationFragmentDirections.actionNavigationSimulationToNavigationLearnSimulationFragment()
                findNavController().navigateSafe(R.id.action_navigation_simulation_to_navigation_learn_simulation_fragment)
            }
*/
            /*ItemSimulation.TIPS_ANSWER -> {
                *//*GlobalScope.launch {
                    try {
                        val query =
                            { id: Int, path: String -> "UPDATE QUESTION_SIMULATION SET path = ${path} WHERE id = ${id} ;" }
                        val url = { id: Int -> "file:///android_asset/video/${id}.mp4" }
                        val file = File(requireActivity().getExternalFilesDir("encode"), "output.txt")
                        val writer = BufferedWriter(FileWriter(file.path))
                        for (i in 1..120) {
                            val link = url(i)
                            val inputStream: InputStream =
                                requireActivity().assets.open("video/${i}.mp4")
                            val path = encryptVideo(inputStream)

                            writer.write(query(i, path))
                            writer.newLine()
                        }
                        writer.close();
                        Log.e(TAG, "Done export", )
                        *//**//*val decryptedData = decryptVideo(encryptedData)

                        val share  = get<SharedPreferences>(named(SHARED_PREFERENCES_TYPE.NORMAL))
                        share.edit().putString("a", encryptedData).apply()

                        // Lưu trữ tệp giải mã vào bộ nhớ để phát lại sau
                        val file = File(requireActivity().getExternalFilesDir("Video"), "video.mp4")
                        val outputStream = FileOutputStream(file)
                        outputStream.write(decryptedData)
                        outputStream.close()

                        // Đường dẫn đến tệp đã giải mã
                        val path: String = file.getPath()
                        Log.e(TAG, "enmcty : ${encryptedData}", )
                        //Log.e(TAG, "path video: ${path}", )*//**//*
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }*//*
            }*/

            else -> {}
        }
    }
}
