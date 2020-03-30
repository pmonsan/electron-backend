export interface IPgApplication {
  id?: number;
  code?: string;
  label?: string;
  partnerCode?: string;
  active?: boolean;
}

export class PgApplication implements IPgApplication {
  constructor(public id?: number, public code?: string, public label?: string, public partnerCode?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
