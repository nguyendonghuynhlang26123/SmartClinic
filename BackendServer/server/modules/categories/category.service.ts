import { CategoryInterface } from "../../interfaces";
import { categoryModel } from "../../models";

export class CategoryService {
  async getCategoryById(categoryId: string) {
    try {
      const category = await categoryModel.findOne({ _id: categoryId });
      if (!category) {
        throw new Error("Not Found Category.");
      }
      return category;
    } catch (error) {
      console.log(error);
      throw new Error("Get Category Error.");
    }
  }

  async getAllCategory() {
    try {
      const categories = await categoryModel.find();
      return categories;
    } catch (error) {
      console.log(error);
      throw new Error("Get All Category Error.");
    }
  }

  async createCategory(data: CategoryInterface) {
    try {
      delete data._id;
      delete data.created_at;
      delete data.updated_at;
      const category = await categoryModel.create(data);
      return category;
    } catch (error) {
      throw new Error("Create Category Error.");
    }
  }
}
