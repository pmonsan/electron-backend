/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PriceDetailComponent } from 'app/entities/price/price-detail.component';
import { Price } from 'app/shared/model/price.model';

describe('Component Tests', () => {
  describe('Price Management Detail Component', () => {
    let comp: PriceDetailComponent;
    let fixture: ComponentFixture<PriceDetailComponent>;
    const route = ({ data: of({ price: new Price(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PriceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PriceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PriceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.price).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
