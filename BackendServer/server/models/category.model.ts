import * as mongoose from "mongoose";

const Schema = mongoose.Schema;
const CategorySchema = new Schema(
  {
    category_name: { type: String, required: true, unique: true },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: "created_at", updatedAt: "updated_at" } }
);

export const category = mongoose.model("categorys", CategorySchema);
