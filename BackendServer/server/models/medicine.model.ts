import * as mongoose from "mongoose";

const Schema = mongoose.Schema;
const MedicineSchema = new Schema(
  {
    medicine_name: { type: String, required: true, unique: true },
    thumbnail: { type: String, default: null },
    price: { type: Number, required: true },
    description: { type: String, default: null },
    direction: { type: String, required: true },
    ingredient: { type: String, required: true },
    category: [{ type: mongoose.Schema.Types.ObjectId, ref: "categories" }],
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: "created_at", updatedAt: "updated_at" } }
);

export const medicineModel = mongoose.model("medicines", MedicineSchema);
