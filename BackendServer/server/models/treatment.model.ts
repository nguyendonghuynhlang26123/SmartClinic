import * as mongoose from 'mongoose';

const Schema = mongoose.Schema;
const TreatmentSchema = new Schema(
  {
    appointment: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'appointments',
    },
    prescription: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'prescriptions',
      default: null,
    },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: 'created_at', updatedAt: 'updated_at' } }
);

export const treatmentModel = mongoose.model('treatments', TreatmentSchema);
