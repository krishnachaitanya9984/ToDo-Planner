package com.krinyny.todoplanner

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.krinyny.tododb.data.ToDoRepositoryImpl
import com.krinyny.tododb.data.ToDoTask
import com.krinyny.todoplanner.ui.event.AddTaskEvent
import com.krinyny.todoplanner.ui.viewmodel.AddToDoViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class AddTodoViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddToDoViewModel
    private val repository: ToDoRepositoryImpl = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        coEvery { repository.getAllTasks() } returns flowOf(emptyList())
        viewModel = AddToDoViewModel(repository)
    }

    @Test
    fun testInitialState() {
        assertThat(viewModel.isLoading.value).isFalse()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAddTodoSuccess() = runTest {
        val taskName = "New task"
        coEvery { repository.addTask(any()) } just Runs
        viewModel.onUIEvent(AddTaskEvent.AddToDoTask(taskName))
        advanceUntilIdle()
        coVerify { repository.addTask(ToDoTask(taskName = taskName)) }
        assertEquals(false, viewModel.isLoading.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAddTodoError() = runTest {
        val taskName = "Error"
        coEvery { repository.addTask(any()) } just Runs
        viewModel.onUIEvent(AddTaskEvent.AddToDoTask(taskName))
        advanceUntilIdle()
        coVerify { repository.addTask(ToDoTask(taskName = taskName)) }
        assertEquals(false, viewModel.isLoading.value)

    }


}