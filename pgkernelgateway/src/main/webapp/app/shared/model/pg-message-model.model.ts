export interface IPgMessageModel {
  id?: number;
  code?: string;
  label?: string;
  comment?: string;
  active?: boolean;
}

export class PgMessageModel implements IPgMessageModel {
  constructor(public id?: number, public code?: string, public label?: string, public comment?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
