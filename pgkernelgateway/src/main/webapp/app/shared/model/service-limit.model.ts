export interface IServiceLimit {
  id?: number;
  limitTypeCode?: string;
  value?: string;
  comment?: string;
  active?: boolean;
  pgServiceId?: number;
}

export class ServiceLimit implements IServiceLimit {
  constructor(
    public id?: number,
    public limitTypeCode?: string,
    public value?: string,
    public comment?: string,
    public active?: boolean,
    public pgServiceId?: number
  ) {
    this.active = this.active || false;
  }
}
