package com.srteam.expensetracker.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;

import com.srteam.expensetracker.utils.ExpensesManager;

import java.util.ArrayList;
import java.util.List;


abstract class BaseExpenseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    /**
     * Indicates if the item at position position is selected
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public void toggleSelection(int position) {
        if (ExpensesManager.getInstance().getSelectedExpensesItems().get(position, false)) {
            ExpensesManager.getInstance().getSelectedExpensesItems().delete(position);
        } else {
            ExpensesManager.getInstance().getSelectedExpensesItems().put(position, true);
        }
        notifyItemChanged(position);
    }

    /**
     * Clear the selection status for all items
     */
    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        ExpensesManager.getInstance().getSelectedExpensesItems().clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    /**
     * Count the selected items
     * @return Selected items count
     */
    public int getSelectedItemCount() {
        return ExpensesManager.getInstance().getSelectedExpensesItems().size();
    }

    /**
     * Indicates the list of selected items
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(ExpensesManager.getInstance().getSelectedExpensesItems().size());
        for (int i = 0; i < ExpensesManager.getInstance().getSelectedExpensesItems().size(); ++i) {
            items.add(ExpensesManager.getInstance().getSelectedExpensesItems().keyAt(i));
        }
        return items;
    }

    public SparseBooleanArray getSelectedBooleanArray() {
        return ExpensesManager.getInstance().getSelectedExpensesItems();
    }

    public void setSelectedItems(SparseBooleanArray selectedItems) {
        ExpensesManager.getInstance().setSelectedItems(selectedItems);
    }

}
