import * as mongoose from "mongoose";

const Schema = mongoose.Schema;
const PrescriptionSchema = new Schema(
  {
    medicine_list: {
      type: {
        medicine: {
          type: mongoose.Schema.Types.ObjectId,
          ref: "medicines",
          required: true,
        },
        note: { type: String, default: null },
      },
      required: true,
    },
    reexam_date: { type: Number, default: null },
    doctor: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "doctors",
      required: true,
    },
    patient: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "patients",
      required: true,
    },
    date: { type: Number, required: true },
    note: { type: String, default: null },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: "created_at", updatedAt: "updated_at" } }
);

export const prescriptionModel = mongoose.model("prescriptions", PrescriptionSchema);