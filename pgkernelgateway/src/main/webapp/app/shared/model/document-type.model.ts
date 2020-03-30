export interface IDocumentType {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class DocumentType implements IDocumentType {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
