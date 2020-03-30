/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ContractDetailComponent } from 'app/entities/contract/contract-detail.component';
import { Contract } from 'app/shared/model/contract.model';

describe('Component Tests', () => {
  describe('Contract Management Detail Component', () => {
    let comp: ContractDetailComponent;
    let fixture: ComponentFixture<ContractDetailComponent>;
    const route = ({ data: of({ contract: new Contract(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ContractDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContractDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContractDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contract).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
