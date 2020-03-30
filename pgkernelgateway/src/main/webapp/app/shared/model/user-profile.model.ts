import { Moment } from 'moment';

export interface IUserProfile {
  id?: number;
  code?: string;
  name?: string;
  creationDate?: Moment;
  description?: string;
  updateDate?: Moment;
}

export class UserProfile implements IUserProfile {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public creationDate?: Moment,
    public description?: string,
    public updateDate?: Moment
  ) {}
}
