import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgResponseCode } from 'app/shared/model/pg-response-code.model';
import { PgResponseCodeService } from './pg-response-code.service';

@Component({
  selector: 'jhi-pg-response-code-delete-dialog',
  templateUrl: './pg-response-code-delete-dialog.component.html'
})
export class PgResponseCodeDeleteDialogComponent {
  pgResponseCode: IPgResponseCode;

  constructor(
    protected pgResponseCodeService: PgResponseCodeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgResponseCodeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgResponseCodeListModification',
        content: 'Deleted an pgResponseCode'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-response-code-delete-popup',
  template: ''
})
export class PgResponseCodeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgResponseCode }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgResponseCodeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgResponseCode = pgResponseCode;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-response-code', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-response-code', { outlets: { popup: null } }]);
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
