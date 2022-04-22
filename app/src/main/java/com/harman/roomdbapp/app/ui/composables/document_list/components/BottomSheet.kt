package com.harman.roomdbapp.app.ui.composables.document_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.ui.composables.style.RobocoFontFamily
import com.harman.roomdbapp.app.ui.viewmodel.BottomSheetViewModel
import com.harman.roomdbapp.domain.model.GravityRecord
import org.koin.androidx.compose.getViewModel
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ButtonSheet(
    viewModel: BottomSheetViewModel = getViewModel(),
    bottomSheetState: ModalBottomSheetState,
    document: String,
    content: @Composable () -> Unit
) {

    if (
        bottomSheetState.progress.from == ModalBottomSheetValue.Expanded &&
        bottomSheetState.progress.to == ModalBottomSheetValue.HalfExpanded
    )
        LaunchedEffect(Unit) {
            bottomSheetState.hide()
        }
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = bottomSheetState,

        sheetContent = {
            val config = LocalConfiguration.current

            Column(
                modifier = androidx.compose.ui.Modifier.height((config.screenHeightDp / 1.15).dp)
            ) {
                if (bottomSheetState.isVisible && document.isNotEmpty()) {
                    MarqueeText(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 30.dp),
                        text = stringResource(id = R.string.document, document),
                        fontSize = 30.sp,
                        fontFamily = RobocoFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    LazyColumn(
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.recycler_padding_bottom))
                    ) {

                        val list =
                            viewModel.getGravityRecordsFromDocuments(document).let {
                                if (it.size < 4) {
                                    val newList = it.toMutableList()
                                    newList.apply {
                                        addAll(
                                            Collections.nCopies(
                                                4 - it.size,
                                                GravityRecord(0f, 0)
                                            )
                                        )
                                    }
                                } else it
                            }
                        items(
                            list
                        ) { entry ->
                            val record = if (entry.record == 0f) "" else entry.timestamp.toString()
                            val timeStamp =
                                if (entry.timestamp == 0L) "" else entry.timestamp.toString()

                            if (list[0] == entry)
                                Divider(Modifier.height(1.dp), color = Color.Black)
                            ModalSheetItem(
                                modifier = Modifier,
                                value = record,
                                date = timeStamp
                            )
                            Divider(Modifier.height(1.dp), color = Color.Black)
                        }
                    }
                }
            }
        },
        content = content
    )
}
