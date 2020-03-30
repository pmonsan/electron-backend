export interface IPgMessageModelData {
  id?: number;
  mandatory?: boolean;
  hidden?: boolean;
  active?: boolean;
  pgDataId?: number;
  pgMessageModelId?: number;
}

export class PgMessageModelData implements IPgMessageModelData {
  constructor(
    public id?: number,
    public mandatory?: boolean,
    public hidden?: boolean,
    public active?: boolean,
    public pgDataId?: number,
    public pgMessageModelId?: number
  ) {
    this.mandatory = this.mandatory || false;
    this.hidden = this.hidden || false;
    this.active = this.active || false;
  }
}
