import * as mongoose from 'mongoose';

const Schema = mongoose.Schema;
const DoctorSchema = new Schema(
  {
    name: { type: String, required: true },
    avatar: { type: String, default: '' },
    bio: { type: String, default: '' },
    department: { type: String, required: true },
    specialty_services: [
      {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'medical-services',
        required: true,
      },
    ],
    hospital: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'hospitals',
      required: true,
    },
    token: { type: String, default: '' },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: 'created_at', updatedAt: 'updated_at' } }
);

export const doctorModel = mongoose.model('doctors', DoctorSchema);
