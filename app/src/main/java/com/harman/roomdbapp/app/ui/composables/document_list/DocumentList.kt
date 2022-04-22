package com.harman.roomdbapp.app.ui.composables.document_list

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.ui.composables.document_list.components.DocumentListItem
import com.harman.roomdbapp.app.ui.composables.document_list.components.MarqueeText
import com.harman.roomdbapp.app.ui.composables.document_list.components.ModalSheetItem
import com.harman.roomdbapp.app.ui.viewmodel.BottomSheetViewModel
import com.harman.roomdbapp.app.ui.viewmodel.DataStorageViewModel
import com.harman.roomdbapp.domain.model.GravityRecord
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import java.util.*

@ExperimentalMaterialApi
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun DocumentList(
    navController: NavController,
    documentsViewModel: DataStorageViewModel = getViewModel(),
    bottomSheetViewModel: BottomSheetViewModel = getViewModel()
) {

    val documentList = mutableStateListOf<String>().apply {
        addAll(documentsViewModel.getGravityDocumentNameList())
    }
    var clickedDocumentName: String by remember {
        mutableStateOf("")
    }
    val coroutine = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            it != ModalBottomSheetValue.HalfExpanded
        }
    )
    if (
        bottomSheetState.progress.from == ModalBottomSheetValue.Expanded &&
        bottomSheetState.progress.to == ModalBottomSheetValue.HalfExpanded
    )
        coroutine.launch {
            bottomSheetState.hide()
        }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = bottomSheetState,
        sheetContent = {
            val config = LocalConfiguration.current

            Column(
                modifier = Modifier.height((config.screenHeightDp / 1.15).dp)
            ) {
                if (bottomSheetState.isVisible && clickedDocumentName.isNotEmpty()) {
                    MarqueeText(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 30.dp),
                        text = stringResource(id = R.string.document, clickedDocumentName),
                        fontSize = 30.sp,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_bold)
                        )
                    )
                    LazyColumn(
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.recycler_padding_bottom))
                    ) {

                        val list =
                            bottomSheetViewModel.getGravityRecordsFromDocuments(clickedDocumentName)
                                .let {
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
        }
    ) {

        Scaffold(backgroundColor = colorResource(id = R.color.pale_yellow)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(id = R.dimen.recycler_padding_Top))
            ) {
                ConstraintLayout(
                    Modifier
                        .padding(
                            top = 40.dp,
                        )
                        .height(IntrinsicSize.Min)
                        .clickable { navController.popBackStack() }
                ) {
                    val (title, icon) = createRefs()
                    val painter = painterResource(id = R.drawable.ic_back_arraow)
                    Image(
                        painter = painter,
                        contentDescription = "back icon",
                        modifier = Modifier.constrainAs(icon) {
                            top.linkTo(title.top, 5.dp)
                            start.linkTo(parent.start, 16.dp)
                            bottom.linkTo(title.bottom, 4.dp)
                            height = Dimension.fillToConstraints
                        }
                    )
                    Text(
                        text = stringResource(id = R.string.back),
                        fontSize = 16.sp,
                        modifier = Modifier.constrainAs(title) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(icon.start, margin = 16.dp)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Divider(modifier = Modifier.height(2.dp))
                Spacer(modifier = Modifier.height(2.dp))
                Divider(modifier = Modifier.height(2.dp))

                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(bottom = 50.dp)
                ) {
                    items(documentList, key = {
                        it
                    }) { document ->
                        val state = rememberDismissState()
                        if (state.isDismissed(DismissDirection.StartToEnd) || state.isDismissed(
                                DismissDirection.EndToStart
                            )
                        ) {
                            documentsViewModel.deleteGravityDocument(document)
                            documentList.remove(document)
                        }
                        SwipeToDismiss(
                            modifier = Modifier
                                .animateItemPlacement()
                                .padding(bottom = 8.dp),
                            state = state, background = {}
                        ) {
                            DocumentListItem(
                                text = stringResource(id = R.string.document, document),
                                modifier = Modifier,
                                onClick = {
                                    coroutine.launch {
                                        clickedDocumentName = document
                                        bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
