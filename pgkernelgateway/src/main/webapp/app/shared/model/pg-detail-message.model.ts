export interface IPgDetailMessage {
  id?: number;
  dataValue?: string;
  active?: boolean;
  pgDataId?: number;
  pgMessageId?: number;
}

export class PgDetailMessage implements IPgDetailMessage {
  constructor(
    public id?: number,
    public dataValue?: string,
    public active?: boolean,
    public pgDataId?: number,
    public pgMessageId?: number
  ) {
    this.active = this.active || false;
  }
}
