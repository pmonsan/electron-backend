import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetailContract } from 'app/shared/model/detail-contract.model';
import { DetailContractService } from './detail-contract.service';

@Component({
  selector: 'jhi-detail-contract-delete-dialog',
  templateUrl: './detail-contract-delete-dialog.component.html'
})
export class DetailContractDeleteDialogComponent {
  detailContract: IDetailContract;

  constructor(
    protected detailContractService: DetailContractService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.detailContractService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'detailContractListModification',
        content: 'Deleted an detailContract'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-detail-contract-delete-popup',
  template: ''
})
export class DetailContractDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ detailContract }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DetailContractDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.detailContract = detailContract;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/detail-contract', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/detail-contract', { outlets: { popup: null } }]);
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
