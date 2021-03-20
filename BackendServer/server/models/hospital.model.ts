import * as mongoose from "mongoose";

const Schema = mongoose.Schema;
const HospitalSchema = new Schema(
  {
    name: { type: String, required: true, unique: true },
    location: { type: String, enum: ["cor_x", "cor_y"], required: true },
    attribute: { type: String, default: null },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: "created_at", updatedAt: "updated_at" } }
);

export const hospital = mongoose.model("hospitals", HospitalSchema);
