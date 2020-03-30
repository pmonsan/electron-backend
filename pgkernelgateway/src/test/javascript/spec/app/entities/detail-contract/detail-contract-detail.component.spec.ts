/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { DetailContractDetailComponent } from 'app/entities/detail-contract/detail-contract-detail.component';
import { DetailContract } from 'app/shared/model/detail-contract.model';

describe('Component Tests', () => {
  describe('DetailContract Management Detail Component', () => {
    let comp: DetailContractDetailComponent;
    let fixture: ComponentFixture<DetailContractDetailComponent>;
    const route = ({ data: of({ detailContract: new DetailContract(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [DetailContractDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DetailContractDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetailContractDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.detailContract).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
