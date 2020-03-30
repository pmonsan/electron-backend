export interface IPgMessageStatus {
  id?: number;
  label?: string;
  active?: boolean;
}

export class PgMessageStatus implements IPgMessageStatus {
  constructor(public id?: number, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
