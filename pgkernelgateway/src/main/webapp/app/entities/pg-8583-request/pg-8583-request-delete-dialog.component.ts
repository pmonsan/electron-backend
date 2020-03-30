import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPg8583Request } from 'app/shared/model/pg-8583-request.model';
import { Pg8583RequestService } from './pg-8583-request.service';

@Component({
  selector: 'jhi-pg-8583-request-delete-dialog',
  templateUrl: './pg-8583-request-delete-dialog.component.html'
})
export class Pg8583RequestDeleteDialogComponent {
  pg8583Request: IPg8583Request;

  constructor(
    protected pg8583RequestService: Pg8583RequestService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pg8583RequestService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pg8583RequestListModification',
        content: 'Deleted an pg8583Request'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-8583-request-delete-popup',
  template: ''
})
export class Pg8583RequestDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pg8583Request }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(Pg8583RequestDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pg8583Request = pg8583Request;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-8583-request', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-8583-request', { outlets: { popup: null } }]);
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
