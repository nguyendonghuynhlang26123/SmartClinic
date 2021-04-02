import { CategoryInterface } from "../../interfaces";
import { categoryModel } from "../../models";

export class CategoryService {
  async getCategoryById(categoryId: string) {
    const category = await categoryModel.findOne({ _id: categoryId });
      if (!category) {
        throw new Error("Not Found Category.");
      }
      return category;
  }

  async getAllCategory() {
    const categories = await categoryModel.find();
      return categories;
  }

  async createCategory(data: CategoryInterface) {
    delete data._id;
    delete data.created_at;
    delete data.updated_at;
    const category = await categoryModel.create(data);
    return category;
  }

  async updateCategoryById(categoryId: string, dataUpdate) {
    const category = await categoryModel.findOne({ _id: categoryId });
    if (!category) throw new Error("Not Found Category.");
    const result = await categoryModel.updateOne(
      { _id: category._id },
      dataUpdate
    );
    return result;
  }

  async deleteCategory(categoryId: string) {
    const category = await categoryModel.findOne({ _id: categoryId });
    if (!category) throw new Error("Not Found Category.");
    const result = await categoryModel
      .deleteOne({ _id: category._id })
      .exec();
    return result;
  }
}
