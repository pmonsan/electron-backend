export interface IPgResponseCode {
  id?: number;
  code?: string;
  label?: string;
  description?: string;
  active?: boolean;
}

export class PgResponseCode implements IPgResponseCode {
  constructor(public id?: number, public code?: string, public label?: string, public description?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
