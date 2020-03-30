import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentType } from 'app/shared/model/document-type.model';
import { DocumentTypeService } from './document-type.service';

@Component({
  selector: 'jhi-document-type-delete-dialog',
  templateUrl: './document-type-delete-dialog.component.html'
})
export class DocumentTypeDeleteDialogComponent {
  documentType: IDocumentType;

  constructor(
    protected documentTypeService: DocumentTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.documentTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'documentTypeListModification',
        content: 'Deleted an documentType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-document-type-delete-popup',
  template: ''
})
export class DocumentTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ documentType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DocumentTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.documentType = documentType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/document-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/document-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
