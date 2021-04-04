import * as mongoose from 'mongoose';

const Schema = mongoose.Schema;
const PatientSchema = new Schema(
  {
    patient_name: { type: String, required: true },
    patient_avatar: { type: String, default: '' },
    patient_gender: { type: String, default: '' },
    patient_dob: { type: Number, default: 0 },
    patient_weight: { type: Number, default: 0 },
    medical_history: {
      type: [
        {
          appointment: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'appointments',
          },
          prescription: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'prescriptions',
          },
          status: {
            type: String,
            default: 'PENDING',
            enum: ['PENDING', 'EXPIRED', 'PROCESSED', 'REEXAM'],
          },
        },
      ],
      default: [],
    },
    token: { type: String, required: true },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: 'created_at', updatedAt: 'updated_at' } }
);

export const patientModel = mongoose.model('patients', PatientSchema);
