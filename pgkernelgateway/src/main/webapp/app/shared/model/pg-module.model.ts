export interface IPgModule {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class PgModule implements IPgModule {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
