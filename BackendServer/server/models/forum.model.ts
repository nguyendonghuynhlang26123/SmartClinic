import { ForumInterface } from './../interfaces';
import * as mongoose from 'mongoose';

const Schema = mongoose.Schema;
const ForumSchema = new Schema(
  {
    topic: { type: String, required: true },
    answers: {
      type: [
        {
          content: { type: String, required: true },
          user_type: {
            type: String,
            required: true,
            enum: ['DOCTOR', 'PATIENT', 'NURSE'],
          },
          display_name: { type: String, required: true },
          user_id: { type: mongoose.Schema.Types.ObjectId, required: true },
        },
      ],
      default: [],
    },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: 'created_at', updatedAt: 'updated_at' } }
);

export const forumModel = mongoose.model('forums', ForumSchema);
