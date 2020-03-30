import { Moment } from 'moment';

export interface IPgMessage {
  id?: number;
  code?: string;
  label?: string;
  messageDate?: Moment;
  comment?: string;
  active?: boolean;
  pgMessageModelId?: number;
  pgMessageStatusId?: number;
}

export class PgMessage implements IPgMessage {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public messageDate?: Moment,
    public comment?: string,
    public active?: boolean,
    public pgMessageModelId?: number,
    public pgMessageStatusId?: number
  ) {
    this.active = this.active || false;
  }
}
