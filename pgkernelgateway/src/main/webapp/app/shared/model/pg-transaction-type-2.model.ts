export interface IPgTransactionType2 {
  id?: number;
  code?: string;
  label?: string;
  description?: string;
  active?: boolean;
}

export class PgTransactionType2 implements IPgTransactionType2 {
  constructor(public id?: number, public code?: string, public label?: string, public description?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
