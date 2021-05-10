import { UpdateQuery } from 'mongoose';
import { ForumInterface, AnswerInterface } from '../../interfaces';
import { forumModel } from '../../models';

export class ForumService {
  async getForumById(forumId: string) {
    try {
      const forum = await forumModel.findOne({ _id: forumId });
      if (!forum) {
        throw new Error('Not Found Forum.');
      }
      return forum;
    } catch (error) {
      console.log(error);
      throw new Error('Get Forum Error.');
    }
  }

  async getAllForum(query?) {
    const perPage = query.per_page ?? 5;
    const page = query.page ?? 1;
    let queryFind = {};
    if (query.search) queryFind = { topic: new RegExp(query.search, 'i') };
    const forums = await Promise.all([
      forumModel.find(queryFind, null, {
        limit: Number(perPage),
        skip: Number((page - 1) * perPage),
        sort: { created_at: 'asc' },
      }),
      await forumModel.countDocuments(queryFind),
    ]);

    return {
      data: forums[0],
      results: forums[1],
      page: page,
      totalPage: Math.ceil(forums[1] / perPage),
    };
  }

  async createForum(data: ForumInterface) {
    delete data._id;
    delete data.created_at;
    delete data.updated_at;
    const forum = await forumModel.create(data);
    return forum;
  }

  async updateForumById(forumId: string, dataUpdate) {
    const forum = await forumModel.findOne({
      _id: forumId,
    });
    if (!forum) throw new Error('Not Found Forum.');
    const result = await forumModel.updateOne({ _id: forum._id }, dataUpdate);
    return result;
  }

  async addChatToForum(forumId: string, chatMessage: AnswerInterface) {
    const forum = await forumModel.findOne({
      _id: forumId,
    });
    if (!forum) throw new Error('Not Found Forum.');
    const update: UpdateQuery<AnswerInterface> = {
      $push: {
        'answers': chatMessage,
      },
    };
    const result = await forumModel.updateOne({ _id: forumId }, update);
    return result;
  }

  async deleteForum(forumId: string) {
    const forum = await forumModel.findOne({
      _id: forumId,
    });
    if (!forum) throw new Error('Not Found Forum.');
    const result = await forumModel.deleteOne({ _id: forum._id }).exec();
    return result;
  }
}
