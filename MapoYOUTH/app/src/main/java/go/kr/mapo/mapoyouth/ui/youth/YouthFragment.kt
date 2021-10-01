package go.kr.mapo.mapoyouth.ui.youth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import go.kr.mapo.mapoyouth.R
import go.kr.mapo.mapoyouth.databinding.FragmentYouthBinding
import go.kr.mapo.mapoyouth.ui.common.ListItemPagerAdapter
import go.kr.mapo.mapoyouth.util.FLAG_YOUTH
import go.kr.mapo.mapoyouth.util.customView.CustomAttr

@AndroidEntryPoint
class YouthFragment : Fragment() {

    private lateinit var binding : FragmentYouthBinding
    private lateinit var pagerAdapter: ListItemPagerAdapter
    private val viewModel : YouthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_youth, container, false)
        pagerAdapter = ListItemPagerAdapter(YouthListAdapter(), null)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val tabList = resources.getStringArray(R.array.youth_tab)
            val tabItem = tabs.getChildAt(0) as ViewGroup
            tabs.getTabAt(0)!!.select().apply { CustomAttr.changeTabsBold(tabItem, 0, tabs.tabCount) }
            viewPager.apply {
                currentItem = 0
                adapter = pagerAdapter
            }
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> tabList[0]
                    1 -> tabList[1]
                    2 -> tabList[2]
                    3 -> tabList[3]
                    else -> tabList[4]
                }
            }.attach()
            viewModel.youthList.observe(viewLifecycleOwner, {
                pagerAdapter.submitYouthData(lifecycle, it)
            })
        }
    }

}