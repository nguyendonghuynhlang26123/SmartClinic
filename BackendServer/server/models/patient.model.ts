import * as mongoose from "mongoose";

const Schema = mongoose.Schema;
const PatientSchema = new Schema(
  {
    patient_name: { type: String, required: true },
    patient_avatar: { type: String, default: null },
    appointment_list: [
      { type: mongoose.Schema.Types.ObjectId, ref: "appointments" },
    ],
    medical_history: { type: [String], default: null },
    token: { type: String, required: true },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: "created_at", updatedAt: "updated_at" } }
);

export const patient = mongoose.model("patients", PatientSchema);
